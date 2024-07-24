-- Lua script for atomic compare and increment operation in a hash
local hash_key = KEYS[1] -- The hash key passed as the first argument

-- Get the current values of 'Number' and 'Max' fields in the hash
local number = tonumber(redis.call('HGET', hash_key, 'Number'))
local max = tonumber(redis.call('HGET', hash_key, 'Max'))

-- Check if 'Number' is less than 'Max'
if number < max then
    -- Increment 'Number' by 1 atomically
    redis.call('HINCRBY', hash_key, 'Number', 1)
    -- Return true to indicate success
    return true
else
    -- Return false to indicate that the operation was not performed
    return false
end