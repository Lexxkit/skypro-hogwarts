package com.lexxkit.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {

    private final Logger logger = LoggerFactory.getLogger(InfoService.class);
    public Integer calculateSumFromIterator() {
        // original stream
        long initTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1).limit(1_000_000).reduce(0, (a, b) -> a + b );
        long cutOffTime = System.currentTimeMillis() - initTime;
        logger.info("Execution in one tread, ms: {}", cutOffTime);

        // calculation using parallel threads in stream
        initTime = System.currentTimeMillis();
        int sum_opt = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        cutOffTime = System.currentTimeMillis() - initTime;
        logger.info("Execution in parallel treads, ms: {}", cutOffTime);

        return sum_opt;
    }
}
