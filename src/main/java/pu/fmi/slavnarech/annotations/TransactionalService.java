package pu.fmi.slavnarech.annotations;

import jakarta.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;

@Target({ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Transactional
@Service
public @interface TransactionalService {}
