import { Button, Card, CardActionArea, CardContent, Typography } from "@mui/material";
import { BouquetFlower, Flower } from "@api/api";
import { Check } from "@mui/icons-material";
import style from "./flowerCard.module.css"



export default function FlowerCardBack(prop : {switch() : void, flower : Flower, decrementQuantity() : void, addToCart(e) : void, bouquetFlower : BouquetFlower, incrementQuantity() : void, bouquetDiff : number}){


    const {bouquetFlower, flower, bouquetDiff} = prop;


    return(
        <Card sx={{

    height: '100%',

    display: 'flex',
    flexDirection: 'column',
        }}>
            <CardActionArea
                sx={{ backgroundColor: "green", color: "whitesmoke" }}
                onClick={() => prop.switch()}
            >
                <CardContent>
                    <p className={style.title} >
                        {flower.kind}
                    </p>
                </CardContent>
            </CardActionArea>

            {/* flex: 1 ensures the layout pushes the buttons down properly */}
            <CardContent sx={{ flex: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                <p className={style.text}>
                    Create a bouquet?<br></br>
                    A {flower.kind} unit costs {flower.avgPrice} <br></br>
                    We have {((flower.quantity - bouquetDiff) - bouquetFlower.quantity)} on stock <br></br>
                </p>

                <div >
                    <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "1rem" }}>
                        <Button onClick={() => prop.decrementQuantity()} variant="contained">-</Button>
                        <p className={style.text} style={{ alignSelf: "center" }} >{bouquetFlower.quantity}</p>
                        <Button onClick={() => prop.incrementQuantity()} variant="contained">+</Button>
                    </div>
                    <div style={{ display: "flex", justifyContent: "right", paddingTop: "1rem" }}>
                        <p className={style.text}>{bouquetFlower.quantity * flower.avgPrice}</p>
                    </div>
                    <div style={{ display: "flex", justifyContent: "right", paddingTop: "1rem" }}>
                        <Button sx={{ color: "green" }} onClick={prop.addToCart} variant="outlined" disabled={bouquetFlower.quantity <= 0}><Check /></Button>
                    </div>
                </div>
            </CardContent>
        </Card>
    )
}
