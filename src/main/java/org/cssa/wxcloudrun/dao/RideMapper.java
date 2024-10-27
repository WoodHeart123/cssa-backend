package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cssa.wxcloudrun.model.Ride;

import java.util.List;

@Mapper
public interface RideMapper {
    Ride getRide(@Param("rideId") Integer rideId);

    List<Ride> getRideList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Ride> getRemovedRideList(@Param("userId") String userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    boolean isRideOwnedByUser(@Param("userId") String userId, @Param("rideId") Integer rideId);

    boolean publishRide(Ride ride);

    boolean updateRide(@Param("userId") String userId, @Param("ride") Ride ride, @Param("ifToPublish") boolean ifPublish);

    boolean removeRide(@Param("rideId") Integer rideId);

    boolean deleteRide(@Param("rideId") Integer rideId);

    boolean isPublished(@Param("rideId") Integer rideId);

}
