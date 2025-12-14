import { api } from "../../api/api";
import { CourseDTO } from "../../models/CourseDTO";

const BASE_PATH = "/courses";

export async function findAllCourses(): Promise<CourseDTO[]> {
  const { data } = await api.get<CourseDTO[]>(`${BASE_PATH}/curso`);
  return data;
}
