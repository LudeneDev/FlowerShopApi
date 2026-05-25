import {useState} from "preact/hooks";
import {Button, Card, CardActionArea, CardContent, TextField, Typography} from "@mui/material";
import {useDataContext} from "../../pages/Home/Provider/dataProvider";



export default function CreateFlowerCard(prop: {selected : Boolean}){

    const {createFlower} = useDataContext();
    const [kind, setKind] = useState("");
    const [price, setPrice] = useState(0);
    const [quantity, setQuantity] = useState(0);

    async function handleCreate() {
        if(price > 0 && quantity > 0 && kind.match(/^[A-Za-z]+$/)){
            await createFlower({kind: kind, avgPrice: price, quantity: quantity});


        }
    }


    return (


        <div style={{
            position: "relative",
            width: "100%",
            height: "100%",
            minHeight: "300px"

        }}>
        <Card sx={{
            display: 'flex',
            flexDirection: 'column',
            gap: 2,
            flex: 1,

        }}>
            <CardActionArea
                sx={{ backgroundColor: "green", color: "whitesmoke" }}
                disabled={!prop.selected}
            >
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        <TextField
                            fullWidth
                            label="Flower kind"
                            value={kind}
                            onChange={(e) => setKind(e.target.value.trim())}
                        />
                    </Typography>
                </CardContent>
            </CardActionArea>


            <CardContent >
                <TextField
                    sx={{my:"1rem"}}
                    fullWidth
                    label="Unit price"
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(Number(e.target.value))}
                />

                <TextField
                    sx={{my:"1rem"}}
                    fullWidth
                    label="Stock quantity"
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(Number(e.target.value))}
                />
            </CardContent>
            <CardContent>
                <Button
                    fullWidth
                    variant="contained"
                    onClick={handleCreate}
                >
                    Create Flower
                </Button>
            </CardContent>







        </Card>
</div>

    )
}