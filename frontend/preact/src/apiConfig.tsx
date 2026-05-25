import { Configuration } from "@api/configuration";
import { getToken } from "./auth";
import { ensureDemoSession } from "./demo";

const BASE_URL = "/api";

export async function createApiConfig(isDemo: boolean): Promise<Configuration> {
  if (isDemo) {
    await ensureDemoSession();
    const token = getToken();
    return new Configuration({
      basePath: BASE_URL,
      baseOptions: {
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      },
    });
  }

  // fallback: basic auth
  return new Configuration({
    basePath: BASE_URL,
    username: "admin",
    password: "secret",
    baseOptions: {
      headers: {
        Authorization: "Basic YWRtaW46c2VjcmV0",
      },
    },
  });
}