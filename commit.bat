@echo off
echo Adicionando arquivos ao stage...
git add .

echo Realizando commit...
git commit -m "feat: adicionar operacoes CRUD para despesas e novas tools de IA" -m "Detalhes das alteracoes:" -m "- Criados UseCases: GetExpenseByIdUseCase, UpdateExpenseUseCase, DeleteExpenseUseCase" -m "- Atualizado ExpenseController com endpoints de GET by id, PUT e DELETE" -m "- Criada ResourceNotFoundException e adicionada ao GlobalExceptionHandler" -m "- Adicionadas novas tools no ExpenseTools: consultarResumoGastos e consultarGastosHoje" -m "- Atualizados SystemPrompts para refletir as novas capacidades" -m "- Implementados e atualizados testes unitarios para Controller, UseCases e Tools"

echo.
echo Commit realizado com sucesso!
echo Para enviar as alteracoes, execute: git push
