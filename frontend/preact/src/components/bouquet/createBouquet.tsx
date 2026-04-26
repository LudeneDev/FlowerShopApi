import { CreateBouquetItem, DefaultApi } from "@api/api";
import { Configuration } from "@api/configuration";


export default async function createBouquet(bouquet : CreateBouquetItem){

          try {
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

            const apiInstance = new DefaultApi(config);

            return (( await apiInstance.bouquetsPost(bouquet)).status);

          } catch (err) {
            console.error("Failed to fetch bouquets:", err);
          }


}