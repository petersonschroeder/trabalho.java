// TransacaoService.java
public interface TransacaoService {
    void registrarTransacao(RequisicaoComBancoDTO dto);
    void limparTransacoes(RequisicaoComBancoDTO dto);
    void excluirPorPeriodo(PeriodoRequestDTO dto);
    EstatisticaResponseDTO estatisticasRecentes(RequisicaoComBancoDTO dto);
    EstatisticaResponseDTO estatisticasPorPeriodo(PeriodoRequestDTO dto);
    TransacaoResponseDTO consultarUltima(RequisicaoComBancoDTO dto);
}
