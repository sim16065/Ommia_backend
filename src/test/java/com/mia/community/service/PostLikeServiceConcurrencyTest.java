package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.entity.Post;
import com.mia.community.entity.User;
import com.mia.community.repository.PostLikeRepository;
import com.mia.community.repository.PostRepository;
import com.mia.community.repository.PostStatRepository;
import com.mia.community.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class PostLikeServiceConcurrencyTest {

    private Long userId;
    private Long postId;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostLikeService postLikeService;
    @Autowired
    private PostStatRepository postStatRepository;

    @BeforeEach
    void setUp() {
        User user = userRepository.save(new User("like@test.com", "password", "tester", null));
        Post post = postRepository.save(new Post(user, "제목", "내용", null));

        userId = user.getId();
        postId = post.getId();
    }

    @AfterEach
    void tearDown() {
        postLikeRepository.deleteAllInBatch();
        postRepository.deleteAll();
        userRepository.deleteAllInBatch();
    }

    @Test
    void 같은_회원이_동시에_좋아요를_누르면_한_번만_반영된다() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();
        List<Throwable> failures = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                ready.countDown();
                try {
                    start.await();
                    postLikeService.addLike(postId, userId);
                    successCount.incrementAndGet();
                } catch (Throwable e) {
                    failures.add(e);
                } finally {
                    done.countDown();
                }
            });
        }
        ready.await();       // 10개 스레드가 모두 준비될 때까지 기다렸다가
        start.countDown();   // 한 번에 출발
        done.await();
        executor.shutdown();

        failures.forEach(e -> System.out.println("실패: " + e.getClass().getSimpleName() + " - " + e.getMessage()));

        assertThat(postLikeRepository.count()).isEqualTo(1);
        assertThat(postStatRepository.findLikeCountByPostId(postId)).isEqualTo(1);
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failures).allMatch(e -> e instanceof CustomException);
    }
}