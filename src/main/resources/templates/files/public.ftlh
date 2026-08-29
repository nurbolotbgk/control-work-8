<#import "../layout.ftlh" as layout>

<@layout.layout title="Публичные файлы">

    <div class="mt-4">

        <h2 class="mb-4">
            Публичные файлы
        </h2>

        <#if files?size == 0>

            <div class="alert alert-info">
                Публичных файлов пока нет.
            </div>

        <#else>

            <div class="table-responsive">

                <table class="table table-bordered table-striped">

                    <thead>
                    <tr>
                        <th>Название</th>
                        <th>Тип</th>
                        <th>Скачиваний</th>
                        <th></th>
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
                                ${file.downloadCount}
                            </td>

                            <td>
                                <a href="/files/public/${file.id}"
                                   class="btn btn-sm btn-primary">
                                    Скачать
                                </a>
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
                               href="/files/public?page=${currentPage - 1}">
                                Назад
                            </a>
                        </li>

                    </#if>


                    <#list 0..totalPages-1 as i>

                        <li class="page-item <#if i == currentPage>active</#if>">

                            <a class="page-link"
                               href="/files/public?page=${i}">
                                ${i + 1}
                            </a>

                        </li>

                    </#list>


                    <#if currentPage lt totalPages - 1>

                        <li class="page-item">
                            <a class="page-link"
                               href="/files/public?page=${currentPage + 1}">
                                Вперёд
                            </a>
                        </li>

                    </#if>

                </ul>

            </nav>

        </#if>

    </div>

</@layout.layout>s