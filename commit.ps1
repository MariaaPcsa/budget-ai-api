Write-Host "Adicionando arquivos ao stage..." -ForegroundColor Cyan
git add .

Write-Host "Realizando commit..." -ForegroundColor Cyan
git commit -m "feat: adicionar operacoes CRUD para despesas e novas tools de IA" `
  -m "Detalhes das alteracoes:" `
  -m "- Criados UseCases: GetExpenseByIdUseCase, UpdateExpenseUseCase, DeleteExpenseUseCase" `
  -m "- Atualizado ExpenseController com endpoints de GET by id, PUT e DELETE" `
  -m "- Criada ResourceNotFoundException e adicionada ao GlobalExceptionHandler" `
  -m "- Adicionadas novas tools no ExpenseTools: consultarResumoGastos e consultarGastosHoje" `
  -m "- Atualizados SystemPrompts para refletir as novas capacidades" `
  -m "- Implementados e atualizados testes unitarios para Controller, UseCases e Tools"

Write-Host "`nCommit realizado com sucesso!" -ForegroundColor Green
Write-Host "Para enviar as alteracoes, execute: git push" -ForegroundColor Yellow
