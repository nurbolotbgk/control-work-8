<#import "../layout.ftlh" as layout>

<@layout.layout title="Вход">

    <div class="row justify-content-center mt-5">

        <div class="col-md-6">

            <h2 class="mb-4">
                Вход
            </h2>


            <#if RequestParameters.error??>

                <div class="alert alert-danger">
                    Неверный email или пароль
                </div>

            </#if>


            <#if RequestParameters.registered??>

                <div class="alert alert-success">
                    Регистрация прошла успешно. Теперь войдите.
                </div>

            </#if>


            <form action="/auth/login"
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
                    Войти
                </button>


                <a href="/auth/register"
                   class="btn btn-link">
                    Регистрация
                </a>

            </form>

        </div>

    </div>

</@layout.layout>