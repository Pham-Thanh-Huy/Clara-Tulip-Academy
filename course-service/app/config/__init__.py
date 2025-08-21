from fastapi import FastAPI
from .eureka_server import lifespan

def build_app() -> FastAPI:
    app = FastAPI(lifespan=lifespan)
    return app