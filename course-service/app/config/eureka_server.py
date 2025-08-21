from contextlib import asynccontextmanager
import py_eureka_client.eureka_client as eureka_client
from fastapi import FastAPI


@asynccontextmanager
async def lifespan(app: FastAPI):
    await  eureka_client.init_async(
        eureka_server="http://localhost:8671/eureka",
        app_name="course-service",
        instance_port=8084,
        # ---> CHANGE NAME SERVICE IF U RUN IN DOCKER IN THE SAME NETWORK
        # (EX: IF EUREKA_SERVER AND COURSE-SERVICE IN SAME NETWORK, instance_host (*localhost*) WILL CHANGE TO *course-serice*
        instance_host="localhost",
        on_error=on_error
    )

    yield


def on_error(err_type:str, err:Exception):
    print(err_type, err, sep=": ")
