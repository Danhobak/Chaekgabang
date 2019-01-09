CREATE TABLE member( 
m_id varchar2(20) primary key, 
m_password varchar2(20), 
m_email varchar2(50), 
m_name varchar2(20), 
m_age number(4), 
m_tel varchar2(20)); 

insert into member values('haha7278','qwertyuio','latuni1214@naver.com','홍길동',33,'010-2333-3333')

select * from member;

commit

create table myBook( 
m_id varchar2(20), 
b_isbn varchar2(100), 
constraint mid_for_mybook foreign key(m_id) references member(m_id), 
constraint bid_for_mybook foreign key(b_isbn) references book(b_isbn) 
);

create table Book( 
b_id varchar2(40) primary key, 
b_isbn varchar2(100) unique not null,
b_title varchar2(60) not null, 
b_image varchar2(140), 
b_author varchar2(40) not null, 
b_price varchar2(40) not null, 
b_discount varchar2(40), 
b_publisher varchar2(40) not null, 
b_pubdate varchar2(16) not null, 
b_description varchar2(1000) 
); 