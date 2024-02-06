package com.hexaware.dao;

import java.sql.Connection;

public interface ServiceProvider {

	Connection getConnection();
}