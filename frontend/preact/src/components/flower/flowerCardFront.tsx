import { Card, CardActionArea, CardContent, Typography } from "@mui/material";
import { Flower } from "../../api";





export default function FlowerCardFront(prop : {switch() : void, selected : boolean, flower : Flower}){


    const {flower} = prop;




    return(

        <Card
            >
              <CardActionArea
                sx={{ backgroundColor: "green", color: "whitesmoke" }}
                disabled={!prop.selected}
                onClick={() => prop.switch()} >
                <CardContent>

                  <Typography gutterBottom variant="h5" component="div">
                    {flower.kind}
                  </Typography>
                </CardContent>
              </CardActionArea>
              <CardContent>
                <Typography variant="body1" >
                  {"A unit costs " + flower.avgPrice} <br /> {"We have " + flower.quantity + " in stock"}
                </Typography>
              </CardContent>
            </Card>
    )
}