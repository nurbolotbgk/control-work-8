<#import "../layout.ftlh" as layout>

<@layout.layout title="Личный кабинет">

    <div class="mt-4">

        <h2 class="mb-3">
            Личный кабинет
        </h2>

        <p>
            <strong>Email:</strong>
            ${user.email}
        </p>


        <hr>


        <h4 class="mb-3">
            Загрузить файл
        </h4>

        <form action="/files/upload"
              method="post"
              enctype="multipart/form-data"
              class="mb-5">

            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}">
            </#if>


            <div class="mb-3">

                <label class="form-label">
                    Выберите файл
                </label>

                <input type="file"
                       name="file"
                       class="form-control"
                       required>

            </div>


            <div class="form-check mb-3">

                <input class="form-check-input"
                       type="checkbox"
                       name="isPublic"
                       value="true"
                       id="isPublic">

                <label class="form-check-label"
                       for="isPublic">
                    Публичный файл
                </label>

            </div>


            <button type="submit"
                    class="btn btn-primary">
                Загрузить
            </button>

        </form>


        <hr>


        <h4 class="mb-3">
            Мои файлы
        </h4>


        <#if files?size == 0>

            <div class="alert alert-info">
                У вас пока нет загруженных файлов.
            </div>

        <#else>

            <div class="table-responsive">

                <table class="table table-bordered table-striped">

                    <thead>
                    <tr>
                        <th>Название</th>
                        <th>Тип</th>
                        <th>Доступ</th>
                        <th>Скачиваний</th>
                        <th>Ссылка</th>
                    </tr>
                    </thead>

                    <tbody>

                    <#list files as file>

                        <tr>

                            <td>
                                ${file.originalName}
                            </td>

                            <td>
                                ${file.contentType!"Неизвестно"}
                            </td>

                            <td>

                                <#if file.isPublic>
                                    <span class="badge bg-success">
                                        Публичный
                                    </span>
                                <#else>
                                    <span class="badge bg-secondary">
                                        Приватный
                                    </span>
                                </#if>

                            </td>

                            <td>
                                ${file.downloadCount}
                            </td>

                            <td>

                                <#if file.isPublic>

                                    <a href="/files/public/${file.id}"
                                       class="btn btn-sm btn-outline-primary">
                                        Скачать
                                    </a>

                                <#else>

                                    <#if file.shareKey??>

                                        <a href="/files/private/${file.shareKey}"
                                           class="btn btn-sm btn-outline-warning">
                                            Скачать по ссылке
                                        </a>

                                    <#else>

                                        <span class="text-muted">
                                            Ссылка уже использована
                                        </span>

                                    </#if>

                                </#if>

                            </td>

                        </tr>

                    </#list>

                    </tbody>

                </table>

            </div>

        </#if>


        <#if totalPages gt 1>

            <nav class="mt-4">

                <ul class="pagination">

                    <#if currentPage gt 0>

                        <li class="page-item">
                            <a class="page-link"
                               href="/profile?page=${currentPage - 1}">
                                Назад
                            </a>
                        </li>

                    </#if>


                    <#list 0..totalPages-1 as i>

                        <li class="page-item <#if i == currentPage>active</#if>">

                            <a class="page-link"
                               href="/profile?page=${i}">
                                ${i + 1}
                            </a>

                        </li>

                    </#list>


                    <#if currentPage lt totalPages - 1>

                        <li class="page-item">
                            <a class="page-link"
                               href="/profile?page=${currentPage + 1}">
                                Вперёд
                            </a>
                        </li>

                    </#if>

                </ul>

            </nav>

        </#if>

    </div>

</@layout.layout>