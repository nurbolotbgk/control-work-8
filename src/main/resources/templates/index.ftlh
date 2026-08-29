<#import "layout.ftlh" as layout>

<@layout.layout title="JavaShareHub">

    <div class="text-center mt-5">

        <h1 class="mb-3">
            JavaShareHub
        </h1>

        <p class="lead mb-4">
            Сервис для загрузки и обмена файлами
        </p>

        <a href="/files/public"
           class="btn btn-primary">
            Публичные файлы
        </a>

        <#if !SPRING_SECURITY_CONTEXT??>

            <a href="/auth/register"
               class="btn btn-warning">
                Регистрация
            </a>

        </#if>

    </div>

</@layout.layout>