import {createApiStore} from "../../utils/createApiStore";

interface KotlinVersionData {
  id: string;
  value: string;
  created: string;
  display: boolean;
}

export const useVersionsStore = createApiStore<string[]>(
  {
    endpoint: "/api/kotlin-versions",
    transform: (data: KotlinVersionData[]) => data
      .filter(item => item.display)
      .sort((a, b) => new Date(a.created).getTime() - new Date(b.created).getTime())
      .map(item => item.value),
    initialData: [],
  }
);
