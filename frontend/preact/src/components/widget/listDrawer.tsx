
import {
  Drawer, Toolbar, Typography, IconButton, Divider, List,
  ListItem, ListItemText
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import Checkout from './checkout';
import createBouquet from '../bouquet/createBouquet';
import { useDataContext } from '../../pages/Home/Provider/dataProvider';
import {CreateBouquetItem} from "@api/model";

export default function ListDrawer({ open, setOpen, flowers, context }: any) {
  const {createBouquet} = useDataContext();
  const orders  = context.orders;
  const checkout = async () => {
     const res = await createBouquet({items : context.orders})
    if(res === 201){
      context.clearBouquet();
      setOpen(false)
    }


  }

  const totalPrice = orders.reduce((sum, order) => {
    const flower = flowers.find(f => f.id === order.flowerId);
    return sum + (flower?.avgPrice || 0) * order.quantity;
  }, 0);

  return (
    <Drawer
      anchor="right"
      open={open}
      onClose={() => setOpen(false)}
    >
      <Toolbar sx={{ justifyContent: 'space-between' }}>
        <Typography variant="h6">Your Bouquet</Typography>
      </Toolbar>
      <Divider />

      <List>
        {orders.map(order => {
          const flower = flowers.find(f => f.id === order.flowerId);
          if (!flower) return null;

          return (
            <ListItem
              key={order.flowerId}
              secondaryAction={
                <IconButton edge="end" onClick={() => context.removeFromBouquet(order.flowerId)} >
                  <DeleteIcon />
                </IconButton>
              }
            >
              <ListItemText
                primary={flower.kind}
                secondary={`Qty: ${order.quantity}`}
              />
            </ListItem>
          );
        })}
      </List>

      <Checkout
        totalPrice={totalPrice}
        orderCount={orders.length}
        onCheckout={checkout}
      />

    </Drawer>
  );
}