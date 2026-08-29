<#import "../layout.ftlh" as layout>

<@layout.layout title="Регистрация">

    <div class="row justify-content-center mt-5">

        <div class="col-md-6">

            <h2 class="mb-4">
                Регистрация
            </h2>


            <#if error??>
                <div class="alert alert-danger">
                    ${error}
                </div>
            </#if>


            <form action="/auth/register"
                  method="post">

                <#if _csrf??>
                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}">
                </#if>


                <div class="mb-3">

                    <label class="form-label">
                        Email
                    </label>

                    <input type="email"
                           name="email"
                           class="form-control"
                           value="${registerDto.email!}"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Пароль
                    </label>

                    <input type="password"
                           name="password"
                           class="form-control"
                           required>

                </div>


                <button type="submit"
                        class="btn btn-primary">
                    Зарегистрироваться
                </button>


                <a href="/auth/login"
                   class="btn btn-link">
                    Уже есть аккаунт
                </a>

            </form>

        </div>

    </div>

</@layout.layout>