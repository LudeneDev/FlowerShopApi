import { Box, Card, CardActionArea, CardContent, Typography } from "@mui/material";
import { Flower } from "@api/api";
import FlowerBouncer, { FlowerBouncerApi } from "./flowerBouncer";
import { useState } from "preact/hooks";
import { Ref, RefObject } from "preact";
import style from "./flowerCard.module.css"





export default function FlowerCardFront(prop : {switch() : void, selected : boolean, flower : Flower, burstRef : RefObject<FlowerBouncerApi | null>, bouquetDiff : number
}){



    const {flower, bouquetDiff} = prop;

const handleReady = (api: FlowerBouncerApi) => {
  prop.burstRef.current = api;
};



    return(
<Card sx={{

    height: '100%',

    display: 'flex',
    flexDirection: 'column',

        }}  >
            <CardActionArea
                sx={{ backgroundColor: "green", color: "whitesmoke" }}
                disabled={!prop.selected}
                onClick={() => {prop.switch()}}

            >
                <CardContent>
                    <p  className={style.title}>
                        {flower.kind}
                    </p>
                </CardContent>
            </CardActionArea>


            <CardContent  >
                <p className={style.text}>
                    {"A unit costs " + flower.avgPrice} <br /> {"We have " + (flower.quantity - bouquetDiff) + " in stock"}
                </p>
            </CardContent>



                <FlowerBouncer   onReady={handleReady} active={prop.selected}/>



        </Card>

    )
}