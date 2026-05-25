type BurstEvent = {
  x: number;
  y: number;
  amount?: number;
  emoji?: string[];
};

type Listener = (e: BurstEvent) => void;

const listeners = new Set<Listener>();

export const burstBus = {
  emit(event) {
    listeners.forEach(fn => fn(event));
  },

  subscribe(fn) {
    listeners.add(fn);
    return () => listeners.delete(fn);
  }
};


export default burstBus