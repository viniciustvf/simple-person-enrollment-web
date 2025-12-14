import { api } from "../../api/api";
import { CourseDTO } from "../../models/couse/CourseDTO";

const BASE_PATH = "/v1/courses";

export async function findAllCourses(): Promise<CourseDTO[]> {
  const { data } = await api.get<CourseDTO[]>(`${BASE_PATH}/curso`);
  return data;
}
