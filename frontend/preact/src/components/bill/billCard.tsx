import { Card, CardContent, Typography } from "@mui/material";
import { Bill } from "@api/api";



export default function BillCard(prop: { bill: Bill }) {

    const { bill } = prop

    const items = bill.items;

    function toDate(date: Date): string {
        const padStart = (value: number): string =>
            value.toString().padStart(2, '0');
        return `${padStart(date.getDate())}/${padStart(date.getMonth() + 1)}/${date.getFullYear()} ${padStart(date.getHours())}:${padStart(date.getMinutes())}`;
    }


    return (

        <div style={{
              position: "relative",
              width: "100%",
              height: "100%",
              minHeight: "300px"

        }}>
        <Card
        sx={{

    height: '100%',

    display: 'flex',
    flexDirection: 'column',

        }}
        >
            <CardContent>
                <Typography sx={style.text}>
                    {"Bill created at: " + toDate(new Date(bill.createdAt))}
                </Typography>
            </CardContent>
            <CardContent>

                <p style={style.text}>Items:</p>
                {items.map(value => {

                    return (value.items.map((value) => {
                        return (
                            <div style={{ display: "flex", justifyContent: "space-between" }}>
                                <div>
                                    <Typography style={style.text}>
                                        {value.flower.kind}
                                    </Typography>
                                </div>
                                <div>
                                    <Typography style={style.text}>
                                        {(value.flower.avgPrice * value.quantity)}
                                    </Typography>
                                </div>
                            </div>)
                    }))

                })}

            </CardContent>
            <CardContent>
                <Typography sx={style.text}>
                    {"Total price: " + bill.totalPrice}
                </Typography>
            </CardContent>
        </Card>
        </div>
    )
}

export function EmptyBillCard(){

    return(
    <Card>

            <CardContent>
                <Typography sx={style.text}>
                    There is no bill currently
                </Typography>
            </CardContent>
        </Card>
    )

}


const style = {
    text : {
        fontSize: "clamp(1rem, 2vw, 2rem)" ,
        padding: 0,
        margin: 0,
    },


}