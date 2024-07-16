INSERT INTO member (email, password)
values
    ('abc@gmail.com', 'zxcv1234'),
    ('eventException@gmail.com', 'zxcv1234'),
    ('noticket@gmail.com', 'zxcv1234');

INSERT INTO consumable_ticket(title, remaining_times, member_id)
values
    ('소모성 이용권', 10, 1),
    ('소모성 이용권', 10, 2);