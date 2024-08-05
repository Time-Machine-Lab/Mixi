local function webRoom(roomId)
    return "Mixi:WebRoom:" .. roomId
end

local function user(userId)
    return "Mixi:User:" .. userId
end

local old_owner = ARGV[1]
local new_owner = ARGV[2]
local OWN = ARGV[3]
local OWNER = ARGV[4]
local CONNECTED = ARGV[5]

local roomId = redis.call('hget', user(old_owner), OWN)

if roomId == false then
    return false
end

if redis.call('hget', webRoom(roomId), OWNER) == false then
    return false
end

if redis.call('hget', user(new_owner), CONNECTED) == roomId then
    if redis.call('hdel', user(old_owner), OWN) < 1 then
        return false
    end

    redis.call('hset', user(new_owner), OWN, roomId)
    redis.call('hset', webRoom(roomId), OWNER, new_owner)
    return true
end
return false