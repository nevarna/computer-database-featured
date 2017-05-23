package com.navarna.computerdb.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.navarna.computerdb.service","com.navarna.computerdb.persistence"})
public class ServiceSpringConfig {
}
