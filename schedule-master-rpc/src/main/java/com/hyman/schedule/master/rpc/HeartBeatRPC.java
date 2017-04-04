package com.hyman.schedule.master.rpc;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.ClusterInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/heartbeat")
@Api(value="HeartBeatRPC",tags = {"HeartBeatRPC"})
public interface HeartBeatRPC{
	
	@Path("/doBeat")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@ApiOperation(value="doBeat",tags = {"HeartBeatRPC.doBeat"})
	Response<ClusterInfo> doBeat(@ApiParam @QueryParam("hostname")String hostname,@ApiParam @QueryParam("port") Integer port);
    
}
