create table if not exists devices (
    id uuid primary key,
    name text not null,
    type varchar(32) not null,
    created_at timestamptz not null default now(),
    version bigint not null default 0
);

create index if not exists idx_devices_created_at on devices (created_at desc);
create index if not exists idx_devices_type on devices (type);