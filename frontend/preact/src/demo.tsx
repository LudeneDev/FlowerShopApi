import axios from "axios";
import { getToken, setToken } from "./auth";

export async function ensureDemoSession() {
  if (getToken()) return;
  const res = await axios.post("/api/demo/start");

  const string = new String(res.data)
  setToken(string);
}