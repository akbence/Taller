-- test data for development purposes only
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

-- Insert an Account Container for user 'john'
INSERT INTO account_container (
    id,
    name,
    owner_id,
    created_at,
    updated_at,
    created_by,
    modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000010',
    'Revolut Accounts',
    '00000000-0000-0000-0000-000000000001',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);

-- Insert first Account under the container
INSERT INTO account (
    id,
    name,
    account_type,
    currency,
    balance,
    account_container_id,
    created_at,
    updated_at,
    created_by,
    modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000100',
    'Daily USD Account',
    'CHECKING',
    'USD',
    1000.00,
    '00000000-0000-0000-0000-000000000010',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);

-- Insert second Account under the same container
INSERT INTO account (
    id,
    name,
    account_type,
    currency,
    balance,
    account_container_id,
    created_at,
    updated_at,
    created_by,
    modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000101',
    'Daily EUR Account',
    'CHECKING',
    'EUR',
    2500.00,
    '00000000-0000-0000-0000-000000000010',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);

-- Insert 3 sample categories for user 'john'

INSERT INTO category (
    id,
    name,
    description,
    owner,
    created_at,
    updated_at,
    created_by,
    modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000201',
    'Groceries',
    'Daily food and household expenses',
    '00000000-0000-0000-0000-000000000001',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);

INSERT INTO category (
    id,
    name,
    description,
    owner,
    created_at,
    updated_at,
    created_by,
    modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000202',
    'Culture',
    'Movies, concerts, exhibitions, and other cultural activities',
    '00000000-0000-0000-0000-000000000001',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);

INSERT INTO category (
    id,
    name,
    description,
    owner,
    created_at,
    updated_at,
    created_by,
    modified_by
) VALUES (
    '00000000-0000-0000-0000-000000000203',
    'Beauty & Health',
    'Cosmetics, personal care, and health-related expenses',
    '00000000-0000-0000-0000-000000000001',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'system',
    'system'
);
