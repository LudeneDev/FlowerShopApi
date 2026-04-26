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

        <Card>
            <CardContent>
                <Typography>
                    {"Bill created at: " + toDate(new Date(bill.createdAt))}
                </Typography>
            </CardContent>
            <CardContent>

                Items:
                {items.map(value => {

                    return (value.items.map((value) => {
                        return (<div style={{ display: "flex", justifyContent: "space-between" }}><div><Typography>{value.flower.kind}</Typography></div> <div><Typography>{(value.flower.avgPrice * value.quantity)}</Typography></div></div>)
                    }))

                })}

            </CardContent>
            <CardContent>
                <Typography>
                    {"Total price: " + bill.totalPrice}
                </Typography>
            </CardContent>
        </Card>
    )
}