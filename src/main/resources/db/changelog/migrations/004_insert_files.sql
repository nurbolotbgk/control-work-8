INSERT INTO files (
    original_name,
    stored_name,
    content_type,
    is_public,
    share_key,
    download_count,
    user_id
)
VALUES (
    'document1.txt',
    'document1.txt',
    'text/plain',
    TRUE,
    NULL,
    0,
    (SELECT id FROM users WHERE email = 'user1@gmail.com')
);

INSERT INTO files (
    original_name,
    stored_name,
    content_type,
    is_public,
    share_key,
    download_count,
    user_id
)
VALUES (
           'photo.jpg',
           'photo.jpg',
           'image/jpeg',
           TRUE,
           NULL,
           2,
           (SELECT id FROM users WHERE email = 'user1@gmail.com')
       );

INSERT INTO files (
    original_name,
    stored_name,
    content_type,
    is_public,
    share_key,
    download_count,
    user_id
)
VALUES (
           'private.txt',
           'private.txt',
           'text/plain',
           FALSE,
           'test-private-key-123',
           0,
           (SELECT id FROM users WHERE email = 'user2@gmail.com')
       );