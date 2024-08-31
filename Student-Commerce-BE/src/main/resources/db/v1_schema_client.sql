CREATE TABLE Role (
    id VARCHAR(40) NOT NULL,
    name VARCHAR(60) NOT NULL,
    label VARCHAR(60) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB;

insert into Role (id, name, label,created_at, last_updated_at, deleted_at) values ('b23a86c9-229d-46e6-a84a-5e0ad33c65de','ROLE_ADMIN', 'Admin',  '2024-06-02 16:32:09.195+05:30', '2024-06-02 16:32:09.195+05:30', null);
insert into Role (id, name, label,created_at, last_updated_at, deleted_at) values ('571cc3bc-89e9-4c5a-bfcc-cf872b72d2d0','ROLE_DELIVERY_PERSON', 'Delivery person','2024-06-02 16:32:09.195+05:30', '2024-06-02 16:32:09.195+05:30', null);
insert into Role (id, name, label,created_at, last_updated_at, deleted_at) values ('972c83Cc-87e3-4z59-bf7c-cf372b72d2d0','ROLE_USER', 'User','2024-06-02 16:32:09.195+05:30', '2024-06-02 16:32:09.195+05:30', null);
insert into Role (id, name, label,created_at, last_updated_at, deleted_at) values ('27mc83Cp-37e3-4z59-bf7c-nf372v72e2d0','ROLE_MANAGER', 'Manager','2024-06-02 16:32:09.195+05:30', '2024-06-02 16:32:09.195+05:30', null);

CREATE TABLE User (
  id varchar(40) NOT NULL,
  name varchar(40) NOT NULL,
  username varchar(40) NOT NULL,
  password varchar(80) NOT NULL,
  email_address varchar(40) NOT NULL,
  phone_number varchar(40) NOT NULL,
  role_id VARCHAR(40) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  last_updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT uk_user_username UNIQUE (username),
  CONSTRAINT uk_user_email UNIQUE  (email_address)
) ENGINE=InnoDB;

CREATE TABLE Cart (
    id VARCHAR(40) NOT NULL,
    user_id VARCHAR(40) NOT NULL,
    product_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    category VARCHAR(255),
    image_url VARCHAR(255),
    quantity INT NOT NULL,
    created_by VARCHAR(40) NOT NULL,
    last_updated_by VARCHAR(40) NOT NULL,
    deleted_by VARCHAR(40),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES User(id)
) ENGINE=InnoDB;

CREATE TABLE address (
    id VARCHAR(40) NOT NULL,
    user_id VARCHAR(40) NOT NULL,
    address1 VARCHAR(255) NOT NULL,
    address2 VARCHAR(255),
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    created_by VARCHAR(40) NOT NULL,
    last_updated_by VARCHAR(40) NOT NULL,
    deleted_by VARCHAR(40),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES User(id)
)ENGINE=InnoDB;

CREATE TABLE orders (
  id VARCHAR(40),
  order_number INTEGER NOT NULL,
  customer_id VARCHAR(40) NOT NULL,
  order_status VARCHAR(255) NOT NULL,
  payment_status VARCHAR(255) NOT NULL,
  payment_method VARCHAR(255) NOT NULL,
  shipping_address_id VARCHAR(40),
  created_by VARCHAR(40) NOT NULL,
  last_updated_by VARCHAR(40) NOT NULL,
  deleted_by VARCHAR(40),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES User(id),
  FOREIGN KEY (shipping_address_id) REFERENCES address(id)
)ENGINE=InnoDB;

CREATE TABLE order_item (
  id VARCHAR(255),
  order_id VARCHAR(255) NOT NULL,
  product_id INT NOT NULL,
  product_name VARCHAR(255) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  quantity INTEGER NOT NULL,
  image_url VARCHAR(255),
  created_by VARCHAR(40) NOT NULL,
  last_updated_by VARCHAR(40) NOT NULL,
  deleted_by VARCHAR(40),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE payment (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL,
    razorpay_order_id VARCHAR(255) NOT NULL,
    razorpay_payment_id VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    created_by VARCHAR(40) NOT NULL,
    last_updated_by VARCHAR(40) NOT NULL,
    deleted_by VARCHAR(40),
    CONSTRAINT payment_razor_id UNIQUE (razorpay_payment_id)
) ENGINE=InnoDB;

