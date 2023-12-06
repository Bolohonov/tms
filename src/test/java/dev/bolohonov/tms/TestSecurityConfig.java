//package dev.bolohonov.tms;
//
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//@TestConfiguration
//public class TestSecurityConfig {
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        // This anonymous class needs for the possibility of using SpyBean in test methods
//        // Lambda cannot be a spy with spring @SpyBean annotation
//        return new JwtDecoder() {
//            @Override
//            public Jwt decode(String token) {
//                return jwt();
//            }
//        };
//    }
//
//    public Jwt jwt() {
//
//        // This is a place to add general and maybe custom claims which should be available after parsing token in the live system
//        Map<String, Object> claims = Map.of(
//                SUB, USER_AUTH0ID
//        );
//
//        //This is an object that represents contents of jwt token after parsing
//        return new Jwt(
//                AUTH0_TOKEN,
//                Instant.now(),
//                Instant.now().plusSeconds(30),
//                Map.of("alg", "none"),
//                claims
//        );
//    }
//}
