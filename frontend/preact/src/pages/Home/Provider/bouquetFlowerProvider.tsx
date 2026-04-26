import { useContext, useState } from 'preact/hooks';
import { createContext } from 'preact/compat';
import { CreateBouquetFlower } from '@api/api';

interface BouquetContextType {
  orders: CreateBouquetFlower[];
  addToBouquet: (flower: CreateBouquetFlower) => void;
  removeFromBouquet: (flowerId: string) => void;
  clearBouquet: () => void;
}

const BouquetContext = createContext<BouquetContextType | undefined>(undefined);

export const useBouquet = () => {
  const context = useContext(BouquetContext);
  if (!context) throw new Error('useBouquet must be used within a Provider');
  return context;
};


export const BouquetFlowerProvider = ({ children }) => {
  const [orders, setOrders] = useState<CreateBouquetFlower[]>([]);


  const addToBouquet = (flower: CreateBouquetFlower) => {
    setOrders(prevOrders => {
      const index = prevOrders.findIndex(o => o.flowerId === flower.flowerId);

      if (index >= 0) {
        const newOrders = [...prevOrders];
        newOrders[index].quantity += flower.quantity;
        return newOrders;
      } else {
        return [...prevOrders, flower];
      }
    });
  };
  const removeFromBouquet = (flowerId) => {
    setOrders(prevOrders => {
      const index = prevOrders.findIndex(o => o.flowerId === flowerId);

      if (index == -1) {
        console.error("There is no Flower with id: " + flowerId)
        return prevOrders;
      } else {
        prevOrders.splice(index, 1)
        return [...prevOrders];
      }
    });
  }
  const clearBouquet = () => {
    setOrders([]);
  }

  return (
    <BouquetContext.Provider value={{ orders, addToBouquet, removeFromBouquet, clearBouquet }}>
      {children}
    </BouquetContext.Provider>
  );
};