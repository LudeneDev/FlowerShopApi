import axios from "axios";

export const DEMO_MODE = getConfig() ;


async function getConfig(){
    return  await axios.get("api/config").then(res => {
        if(res.status !== 200 || res.data.mode !== "demo"){
            return false
        }
        return true;
        }
    )
}