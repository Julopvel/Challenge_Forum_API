package forum.api.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBcrypt {

    public static void main(String[] args) {

        String password = "123456";
        //Password encoder using BCrypt.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode(password);

        System.out.println("No encriptado: " + password);
        System.out.println("Encriptado: " + hashedPassword);

        // Verifies if the password matches the generated hash (just for demonstration)
        boolean matches = passwordEncoder.matches(password, hashedPassword);
        System.out.println("¿Contraseña coincide con el hash? " + matches);

    }
}
