package com.hyman.schedule.slave.rpc;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hyman.schedule.common.bean.JobInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/jobclient")
@Api(value="JobClientRPC", tags = {"JobClientRPC"})
public interface JobClientRPC{
	
	@Path("/distribute")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value="distribute",tags = {"JobClientRPC.distribute"})
	boolean distribute(@ApiParam JobInfo jobInfo);
    
}
