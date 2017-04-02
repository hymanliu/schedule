package com.hyman.schedule.master.rpc;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/hello")
@Api(value="HelloRPC", tags = {"HelloRPC"})
public interface HelloRPC{
	
	@Path("/say")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@ApiOperation(value="say",tags = {"HelloRPC.say"})
	String say(@ApiParam @QueryParam("name")String name);
    
}
