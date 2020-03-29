package com.lin.imissyou.optional;


import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void testOptional() {
        Optional<String> empty = Optional.empty();
        empty.get();
    }
}
