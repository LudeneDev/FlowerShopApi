import { useContext, useState, useEffect, useRef } from 'preact/hooks';
import { Flower, Bill, DefaultApi } from "@api/api"
import { createContext } from 'preact';
import { createApiConfig } from '../../../apiConfig';
import { DEMO_MODE } from '../../../config';
import { AxiosError } from 'axios';
import { ensureDemoSession } from '../../../demo';
import {CreateBouquetItem, CreateFlower} from "@api/model";


interface DataContextType {
  flowers: Flower[];
  bills: Bill[];
  loading: boolean;
  createFlower: (data : CreateFlower) => Promise<201 | undefined>;
  createBouquet: (data: CreateBouquetItem) => Promise<201 | undefined>;
}

const DataContext = createContext<DataContextType | undefined>(undefined);

export const useDataContext = () => {
  const context = useContext(DataContext);
  if (!context) throw new Error('useDataContext must be used within a Provider');
  return context;
};

export const DataProvider =  ({ children }) => {
  const [flowers, setFlowers] = useState<Flower[]>([]);
  const [bills, setBills] = useState<Bill[]>([]);
  const [loading, setLoading] = useState(true);
  const apiRef = useRef<DefaultApi | null>(null);




   async function initApi () {

    const config = await createApiConfig(DEMO_MODE);

    apiRef.current = new DefaultApi(config);
  }





useEffect(() => {
  const init = async () => {

    await initApi();

    await fetchData();
  };

  init();
}, []);


  const fetchData = async () => {
    setLoading(true);

    try {
      const [flowersRes, billsRes] = await Promise.all([
        request(() => apiRef.current!.flowersGet()),
        request(() => apiRef.current!.billsGet())
      ]);

      setFlowers(flowersRes.data);
      setBills(billsRes.data);

    } finally {
      setLoading(false);
    }
  };

  let refreshingSession = false;

  async function withAuthRetry<T>(
      request: () => Promise<T>
  ): Promise<T> {
    try {
      return await request();

    } catch (e: any) {

      if (e?.status === 403 && !refreshingSession) {
        refreshingSession = true;

        // Optional UI message
        (window as any).showSessionToast?.();

        try {
          await window.cookieStore.delete("token");

          await ensureDemoSession();

          await initApi();

          // small delay so user sees the message
          setTimeout(() => {
            window.location.reload();
          }, 1500);

        } catch (refreshError) {
          console.error(refreshError);

          window.location.reload();
        }

        // prevent hanging promise
        return new Promise(() => {});
      }

      throw e;
    }
  }
  async function request <T>(fn: () => Promise<T>)
  {
    if (DEMO_MODE) {
      return withAuthRetry(fn);
    }
    return fn();
  }

  const createFlower = async (data: CreateFlower) => {
    return await request(() => apiRef.current!.flowersPost(data))
        .then(async s => {
      if(s.status === 201) {
        await fetchData();
        return 201
      }
    })

  };

  const createBouquet = async (data: CreateBouquetItem)  => {
    return await request(() => apiRef.current!.bouquetsPost(data)).then(async s => {
      if(s.status === 201){
        await fetchData();
        return 201
      }
    })

  };



  return (
    <DataContext.Provider value={{ flowers, bills, loading, createFlower, createBouquet }}>
      {children}
    </DataContext.Provider>
  );
};


