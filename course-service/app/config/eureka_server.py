from contextlib import asynccontextmanager
import py_eureka_client.eureka_client as eureka_client
from fastapi import FastAPI


@asynccontextmanager
async def lifespan(app: FastAPI):
    await  eureka_client.init_async(
        eureka_server="http://localhost:8671/eureka",
        app_name="course-service",
        instance_port=8084,
        instance_host="localhost", # ---> CHANGE NAME SERVICE IF U RUN IN DOCKER IN THE SAME NETWORK (EX:
        on_error=on_error
    )

    yield


def on_error(err_type:str, err:Exception):
    print(err_type, err, sep=": ")
