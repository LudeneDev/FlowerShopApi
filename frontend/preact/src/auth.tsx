export function getToken(): string | null {
  const match = document.cookie.match(/token=([^;]+)/);

  return match ? match[1] : null;
}

export function setToken(token: string) {
  document.cookie = `token=${token}; path=/`;
}