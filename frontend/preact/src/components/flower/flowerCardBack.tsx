import { Button, Card, CardActionArea, CardContent, Typography } from "@mui/material";
import { BouquetFlower, Flower } from "../../api";
import { Check } from "@mui/icons-material";



export default function FlowerCardBack(prop : {switch() : void, flower : Flower, decrementQuantity() : void, addToCart() : void, bouquetFlower : BouquetFlower, incrementQuantity() : void}){

    const {flower} = prop;
    const {bouquetFlower} = prop;

    return(
        <Card
            >
              <CardActionArea
                sx={{ backgroundColor: "green", color: "whitesmoke" }}
                onClick={() => prop.switch()} >
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    {flower.kind}
                  </Typography>

                </CardContent>
              </CardActionArea>
              <CardContent >
                <Typography variant="body1">


                  Create a bouquet?<br></br>A {flower.kind} unit costs {flower.avgPrice} <br></br> We have {flower.quantity} on stock <br></br>

                </Typography>


                <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "1rem" }}>
                  <Button onClick={()  => prop.decrementQuantity()} variant="contained">-</Button><Typography sx={{ alignSelf: "center" }} variant="body1" >{bouquetFlower.quantity}</Typography><Button onClick={() => prop.incrementQuantity()} variant="contained">+</Button>
                </div>
                <div style={{ display: "flex", justifyContent: "right", paddingTop: "1rem" }}>
                  <Typography variant="body1" >{bouquetFlower.quantity * flower.avgPrice}</Typography>
                </div>
                <div style={{ display: "flex", flex: "1 0 1", justifyContent: "right", paddingTop: "1rem" }}>

                  <Button sx={{ color: "green" }} onClick={() => prop.addToCart()} variant="outlined"><Check /></Button>
                </div>
              </CardContent>
            </Card>
    )
}