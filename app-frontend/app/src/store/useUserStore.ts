import {create} from "zustand";
import {persist} from "zustand/middleware";

interface User {
  id: string;
  username: string;
  avatar: string;
  email?: string;
  profileUrl?: string;
}

interface UserState {
  user: User | null;
  isLoading: boolean;
  login: (userData: User) => void;
  logout: () => void;
  fetchUser: () => Promise<void>;
}

export const useUserStore = create<UserState>()(
  persist(
    (set, get) => ({
      user: null,
      isLoading: false,

      login: (userData: User) => {
        set({ user: userData });
      },

      logout: () => {
        set({ user: null });
        // Clear session/cookies if needed
        fetch('/auth/logout', { method: 'POST' }).catch(() => {});
      },

      fetchUser: async () => {
        set({ isLoading: true });
        try {
          const response = await fetch('/auth/user');
          if (response.ok) {
            const userData = await response.json();
            set({ user: userData });
          } else {
            set({ user: null });
          }
        } catch (error) {
          console.error('Failed to fetch user:', error);
          set({ user: null });
        } finally {
          set({ isLoading: false });
        }
      },
    }),
    {
      name: 'user-storage',
      partialize: (state) => ({ user: state.user }),
    }
  )
);