import {  useContext, useState, useEffect } from 'preact/hooks';
import {Flower, Bill, DefaultApi} from "@api/api"
import {Configuration} from '@api/configuration'
import { createContext } from 'preact';


interface DataContextType {
  flowers: Flower[];
  bills: Bill[];
  loading: boolean;
  refetch: () => void;
}

const DataContext = createContext<DataContextType | undefined>(undefined);

export const useDataContext = () => {
  const context = useContext(DataContext);
  if (!context) throw new Error('useDataContext must be used within a Provider');
  return context;
};

export const DataProvider = ({ children }) => {
  const [flowers, setFlowers] = useState<Flower[]>([]);
  const [bills, setBills] = useState<Bill[]>([]);
  const [loading, setLoading] = useState(true);
  const config = new Configuration({
            username: "admin",
            password: "secret",
          basePath: 'http://localhost:8080/api',
          baseOptions: {
            headers: {
              'Authorization': "Basic YWRtaW46c2VjcmV0"
            }
          }
        });

  const fetchData = async () => {
    setLoading(true);
    try {
        console.log("run")
      const api = new DefaultApi(config);
      const [fRes, bRes] = await Promise.all([
        api.flowersGet(),
        api.billsGet()
      ]);
      setFlowers(fRes.data);
      setBills(bRes.data);
    } catch (e) {
      console.error("Data fetch failed:", e);
    } finally {
      setLoading(false);
    }
  };


  useEffect(() => {
    fetchData();
  }, []);

  return (
    <DataContext.Provider value={{ flowers, bills, loading, refetch: fetchData }}>
      {children}
    </DataContext.Provider>
  );
};