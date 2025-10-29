-- test data for app_user table
INSERT INTO app_user (
    id, username, created_at, updated_at, created_by, modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000001',
    'john',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);