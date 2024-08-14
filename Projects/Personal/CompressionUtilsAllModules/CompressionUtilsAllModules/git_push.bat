@echo off

pushd %~dp0\..\..\..\..
call git_push %1
call git push vitesco_remote
popd
