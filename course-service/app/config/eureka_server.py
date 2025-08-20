from contextlib import asynccontextmanager
import py_eureka_client.eureka_client as eureka_client

@asynccontextmanager
async def life_span():
    eureka_client.init(
        eureka_server="http://localhost:8671/eureka",
        app_name="course-service",
        instance_port=8084,
        on_error=on_error
    )

    yield


def on_error(err_type:str, err:Exception):
    print(err_type, err, sep=": ")
